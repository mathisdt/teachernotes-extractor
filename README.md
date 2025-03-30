![license](https://img.shields.io/github/license/mathisdt/teachernotes-extractor.svg?style=flat) [![Build](https://github.com/mathisdt/teachernotes-extractor/actions/workflows/build.yaml/badge.svg)](https://github.com/mathisdt/teachernotes-extractor/actions) [![last released](https://img.shields.io/github/release-date/mathisdt/teachernotes-extractor.svg?label=last%20released&style=flat)](https://github.com/mathisdt/teachernotes-extractor/releases)

# TeacherNotes Extractor

This tool can take a backup file from [TeacherNotes](https://play.google.com/store/apps/details?id=com.apps.ips.teachernotes3) 
and convert it to a nicely formatted PDF. This way the data doesn't have to be sent anywhere, you can do it locally on your device(s).

## Prerequisites

You need Java 21 or later to run this application. Please look [here](https://adoptium.net/de/temurin/releases/) if you don't have it yet.

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

# Build using Earthly

The CI build of this project uses [Earthly](https://docs.earthly.dev/), which in turn uses
container virtualization (e.g. Docker or Podman). You can also run the build locally (if you
have Earthly as well as an OCI compatible container engine installed) by executing
`earthly +build`. This will create a container with everything needed for the build,
create the package inside it and then copy the results to the directory `target` for you.
