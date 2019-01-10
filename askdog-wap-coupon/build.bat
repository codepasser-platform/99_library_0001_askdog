rmdir /S /Q build
rem mkdir build\css\lib

xcopy /sy source\lib build\lib\
rem xcopy /sy source\css\lib build\css\lib

node r.js -o build.js
start cmd /c gulp

CMD /K