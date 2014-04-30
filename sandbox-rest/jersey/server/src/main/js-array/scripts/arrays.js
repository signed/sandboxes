Array.dim = function (dimension, initial) {
    var a = [], i;
    for (i = 0; i < dimension; i += 1) {
      a[i] = initial;
    }
    return a;
};

e

console.log('removing elements');
var anArray = [0,1,2,3];
delete anArray[2];
anArray.splice(2,2, 45);
console.log(anArray);



console.log('index operations');
var array = [];
array[1] = 'thomas';
console.log(array[0]);
console.log(array[1]);
console.log(array.length);
console.log(array[30]);
