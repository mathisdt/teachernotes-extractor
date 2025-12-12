# TeacherNotes Extractor

This tool can take a backup file from [TeacherNotes](https://play.google.com/store/apps/details?id=com.apps.ips.teachernotes3) 
and convert it to a nicely formatted PDF. This way the data doesn't have to be sent anywhere, you can do it locally on your device(s).

## Prerequisites

You need Java 17 or later to run this application. Please look [here](https://adoptium.net/de/temurin/releases/) if you don't have it yet.

## Usage

The first argument is required, it's the name of the backup file from TeacherNotes.

The second argument is optional: you can provide a target file name. If you don't use this argument,
the name of the source file, suffixed with a timestamp and the extension `.pdf`, is used as target.

## Example wrapper script

This could be used as a wrapper for the Java application (put it in the same directory as the JAR file):

```
#!/bin/sh
if [ -z "$1" ]; then
  echo "no TeacherNotes backup file given"
  exit 1
fi
DIR=$(dirname $(readlink -f "$0"))
PDF=$(echo "$1" | sed -e 's#.txt$##' -e 's#$#.pdf#')
java -jar "$DIR/teachernotes-extractor*.jar" "$1" "$PDF"
```

# License

This project is licensed under GPL v3. If you submit or contribute changes, these are automatically licensed
under GPL v3 as well. If you don't want that, please don't submit the contribution (e.g. pull request)!
