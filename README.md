# Introduction

This project contains templates for generating arbitrary files and directory structures. It can be used hence as a
template engine to generate software projects.

# Requirements

- Python 3

# Project structure

This is the project structure:

```
├── bin/                    : Directory containing test scripts to generate & build templates
├── common                  : Common utilities.
│   └── utils.py            : Common utilities.
├── generate                : Main program.
├── README.md               : This file.
└── templates               : Contains the templates.
    └── <template-name>     : Contains the template with name <template-name>
        ├── run_template.py : Knowns how to apply the template <template-name>. Must have this name.
        └── template        : Contains the template's directory and file structure. Must have this name.
```

# How it works

To generate a template enter:

    $ ./generate <service-type> <service-name> <package-name>

Templates are generated in the `output` directory of the root of the repo. Files might contain template variables, which
are strings that are surrounded by two `@` (e.g. `@@name@@`). File names and directory names might also contain
template variables. Files are copied verbatim but template variables are replaced with user-provided values.

# How to add a new JAVA template

1. Create a sub-directory `<template-name> in the `templates` directory. `<template-name>` is the template name.
2. Add a `run_template.py` script that knows how to apply the template. See existing examples.
3. Create a sub-directory in `java/templates/<template-name>` called `template`. This contains the arbitrary directory
   structure that is used to apply the template.

# Caveats

File permissions are not preserved when copying in general.  That being said, all executable bits are applied on the destination file when any are present on the source file.
