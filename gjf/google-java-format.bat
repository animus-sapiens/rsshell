@echo off
@break off
@title Google Java Format
@color 0a
@cls
setlocal EnableDelayedExpansion

CD /d "%~dp0"

SET /A ARGS_COUNT=0
FOR %%A in (%*) DO SET /A ARGS_COUNT+=1


IF %ARGS_COUNT% == 0 (
  ECHO "No arguments supplied."
  EXIT /B 5
) ELSE (
    IF "%1" == "format" (
        FOR /F "delims=" %%f IN ('where /r ..\ *.java') DO (
            SET "FILE=%%f"

            java -jar google-java-format-1.18.1-all-deps.jar -r !FILE!
        )
        ECHO "google-java-format completed."
    ) ELSE (
        IF "%1" == "verify" (
           FOR /F "delims=" %%f IN ('where /r ..\ *.java') DO (
               SET "FILE=%%f"
               FOR /F "delims=" %%O IN ('java -jar google-java-format-1.18.1-all-deps.jar -n !FILE!') DO (
                 IF "%%O" NEQ  "" (
                    ECHO %%O not compliant to google-java-format.
                    EXIT /B 5
                 )
               )
           )
            ECHO "google-java-format verification completed."
        ) ELSE (
            ECHO "Wrong argument."
            EXIT /B 5
        )
    )
)



