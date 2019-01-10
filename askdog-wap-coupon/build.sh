#!/usr/bin/env bash
rm -R ./build
mkdir -p ./build/lib
#cp -r ./source/lib ./build/lib
nodejs copy.js
nodejs r.js -o build.js
gulp
#cp ./source/lib/jquery/jquery.min.js ./build/lib/jquery/
