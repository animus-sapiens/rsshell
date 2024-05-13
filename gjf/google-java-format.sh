#!/usr/bin/env bash

cd "$(dirname "$0")"
if [ $# -eq 0 ]
  then
    echo "No arguments supplied."
    exit 1
else
   if [ $1 == "format" ]
     then 
       java -jar google-java-format-1.18.1-all-deps.jar  -i $(find ../ -type f -name "*.java")
       echo "google-java-format completed."
     elif [ $1 == "verify" ]
       then
         NOT_FORMATED_FILES=$(java -jar google-java-format-1.18.1-all-deps.jar  -n   $(echo  $(find ../ -type f -name "*.java")) |  wc -l)
         if [ $NOT_FORMATED_FILES == "0" ]
           then
            echo "All files compliant with google-java-format."
         else
            echo "${NOT_FORMATED_FILES} file/s not compliant to google-java-format."
            exit 1
         fi
         echo "google-java-format verification completed."
     else
       echo "Wrong argument."; exit 1
   fi
fi
