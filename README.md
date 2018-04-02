# PrettyPrintJson

## 1. Description:

PrettyPrintJSON is a command line utility Given a JSON document, pretty print it - either in-place or output to the console.

eg:

$ cat file.json

[{

"name": "Nico Williams",

"parents": ["Bob",

"Wilma"

], "student": true

}, {"name": "Nico Williams",

"parents": ["Carl","Carly"

],"student": true

}

]

When we run PrettyPrintJSON on it,

## $ java -jar PrettyPrintJSON --from-file=file.json

    [

        {

            "name": "Nico Williams",

            "parents": [

                "Bob",

                "Wilma"

            ],

            "student": true

        },

        {

            "name": "Nico Williams",

            "parents": [

                "Carl",

                "Carly"

            ],

            "student": true

        }

    ]

It pretty prints the given JSON.

## 2 API

PrettyPrintJSON should support the following operations (ideally as command line parameters):

So, for ex: I should be able to call

$ java PrettyPrintJSON --replace --indent=3 --from-file=file.json

or,

$ java PrettyPrintJSON --compact --from-file=file.json

### 2.1 --from-file:
    Read from file.

### 2.2 --compact-output:

    By default, PrettyPrintJSON pretty-prints JSON output. Using this option will result in more compact

    output by outputting everything on a single line.

### 2.3 --tab:

    Use a tab for each indentation level instead of the default of two spaces.

### 2.4 --indent n:

    Use the given number of spaces(or tabs) (no more than 8) for indentation.

### 2.5 --replace:

    Replace the contents of the given json file instead of outputting it to the console.
