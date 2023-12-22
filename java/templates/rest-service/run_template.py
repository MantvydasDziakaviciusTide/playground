########################################################################################################################
# This template creates a standard REST project.
#
# Some dummy test classes are generated. Inspect them and remove them when starting development.
# Don't forget to update the README.md file!
########################################################################################################################
from common.utils import apply_template


def additional_info():
    '''
    Provides additional info before generating the files.
    '''
    print("\nOK. Going to build now. Remember to update the README.md file and scripts permissions [ENTER]\n")
    input("")


def ask_substitutions():
    '''
    Asks the user for the necessary template variables.
    '''
    print("This template generates a standard REST service. Please enter the template params below.\n")
    substitutions = {}

    service_name = input("Service name (convention <string>-<string>-service): ")

    if len(service_name) > 24:
        print("\nYou have entered a service name which is greater than max number of characters (24)")
        print("\nExit file now [ENTER]\n")
        input("")
        exit(1)

    substitutions["@@service-name@@"] = service_name

    package_name = input("Package name without co.tide prefix: ")
    substitutions["@@package-name@@"] = package_name

    db_name = input("Database name (convention <string>_<string>_service): ")
    substitutions["@@db-name@@"] = db_name

    additional_info()

    return substitutions


if __name__ == "__main__":
    apply_template(__file__, ask_substitutions)
