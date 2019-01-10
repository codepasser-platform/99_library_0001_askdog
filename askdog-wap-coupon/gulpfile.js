var gulp = require('gulp');

/*var sass = require('gulp-sass');
 gulp.task('sass-compile', function () {
 gulp.src('./source/style/!**!/!*.scss')
 .pipe(sass()).pipe(gulp.dest('./assets/style'));
 });

 gulp.task('html-compile', function () {
 gulp.src('./source/!**!/!*.html').pipe(gulp.dest('./assets'));
 });*/

var templateCache = require('gulp-angular-templatecache');

gulp.task('default', function () {
    return gulp.src('source/views/**/*.html')
        .pipe(templateCache('template.js', {
            module: 'app.template',
            root: 'views/',
            standalone: true,
            templateHeader: 'define([\'angular\'], function() { \'use strict\'; angular.module("<%= module %>"<%= standalone %>).run(["$templateCache", function($templateCache) {',
            templateBody: '$templateCache.put("<%= url %>",\'<%= contents %>\');',
            templateFooter: '}]);});'
        }))
        .pipe(gulp.dest('build/script/app'));
});