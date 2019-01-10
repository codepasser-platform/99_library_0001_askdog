rm -R ./build
mkdir -p ./build/css/lib

cp -r ./src/lib ./build/lib
cp -r ./src/css/lib ./build/css/lib

nodejs r.js -o build.js
gulp

sed -i "s/192.168.1.123:90/www.askdog.com/g" ./build/js/app/store.js
