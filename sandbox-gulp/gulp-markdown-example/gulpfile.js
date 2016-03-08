var gulp = require('gulp');
var del = require('del');
var markdown = require('gulp-markdown');

gulp.task('clean', function(cb) {
  del(['out'], cb);
});

gulp.task('html', function(){
    return gulp.src('src/markdown/**/*.md')
        .pipe(markdown())
        .pipe(gulp.dest('out/markdown'));
});

gulp.task('default', ['clean', 'html']);
 