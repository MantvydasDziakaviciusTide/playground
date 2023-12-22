import os
import stat
import shutil


def apply_string_substitution(string, substitutions):
    '''
    Applies the passed substitutions to the provided string.
    :param string: The string potentially containing template variables (e.g. @@name@@).
    :param substitutions: Dictionary mapping template variables to their substitution (i.e. @@name@@ -> John).
    :return: A new string with template variable substitution applied.
    '''
    new_string = string

    for key in substitutions:
        new_string = new_string.replace(key, substitutions[key])

    return new_string


def apply_substitution(source_file, dest_file, substitutions):
    '''
    Copies a source file to a dest file applying template variable substitutions.
    :param source_file: The path of the source file.
    :param dest_file: The path of the destination file.
    :param substitutions: Dictionary mapping template variables to their substitution (i.e. @@name@@ -> John).
    '''
    os.makedirs(os.path.dirname(dest_file), exist_ok=True)

    try:
        with open(source_file, encoding="utf-8") as source:
            with open(dest_file, "w", encoding="utf-8") as dest:
                for line in source:
                    line = apply_string_substitution(line, substitutions)
                    dest.write(line)
    except UnicodeDecodeError:
        print("WARN: previous file copied as binary")
        shutil.copy(source_file, dest_file)


def apply_executable_permissions(source_file, dest_file):
    '''
    Apply executable bits (user + group + other) on the destination file if any bit present on source file.
    :param source_file: The path of the source file.
    :param dest_file: The path of the destination file.
    '''
    all_executable_bits = stat.S_IXUSR | stat.S_IXGRP | stat.S_IXOTH

    source_file_permissions = os.stat(source_file).st_mode
    dest_file_permissions = os.stat(dest_file).st_mode
    if (source_file_permissions & all_executable_bits) > 0:
        os.chmod(dest_file, dest_file_permissions | all_executable_bits)


def apply_template(template_file_path, substitutions_callback):
    '''
    Applies a template file.
    :param template_file_path: The path of the template Python script relative to the root of the repo.
    :param substitutions_callback: The function that provided the substitutions.
    :return:
    '''
    substitutions = substitutions_callback()
    source_dir = "%s/template" % os.path.dirname(template_file_path)
    dest_dir = "%s/output" % os.getcwd()

    os.chdir(source_dir)

    for dir_name, subdir_list, file_list in os.walk('.'):
        for file_name in file_list:
            source_file = "%s/%s" % (dir_name, file_name)
            dest_file = "%s/%s/%s" % (dest_dir, dir_name, file_name)

            # The substitute value might contain a '.' (e.g. 'co.tide.test')
            sanitised_subs = {name:value.replace('.', '/') for (name,value) in substitutions.items()}
            # The destination path might also contain template variables (e.g. /home/@@username@@).
            dest_file = apply_string_substitution(dest_file, sanitised_subs)

            print("Applying %s -> %s" % (source_file, dest_file))
            apply_substitution(source_file, dest_file, substitutions)
            apply_executable_permissions(source_file, dest_file)
