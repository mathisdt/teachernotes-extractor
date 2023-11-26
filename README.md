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
