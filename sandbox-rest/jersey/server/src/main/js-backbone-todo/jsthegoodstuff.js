var callMe = function(){
    var str="20110912170401";
    var year = str.substring(0,4);
    var month = str.substring(4,6);
    var dayOfMonth = str.substring(6,8);
    var hourOfDay = str.substring(8,10);
    var minutesOfHour = str.substring(10,12);
    var secondsOfMinute = str.substring(12,14);
    var shiftedMonth = parseInt(month, 10)-1;
    console.log(new Date(year, shiftedMonth , dayOfMonth, hourOfDay, minutesOfHour, secondsOfMinute).toString());
}
callMe();

var anObject = {
    value: 7,
    getValue : function(){
        return this.value;
    }
};

anObject.anotherFunction = function(){
    return this.value;
};

//console.log(anObject.getValue()+' and '+anObject.anotherFunction());


var aFunction = function(x, y){
    return 'a constant string';
};

var literalObject ={
    value:'constant'
}

//aFunction.call(literalObject);

var superClass ={
    'property':'super',
    'overridden' : 'super in the house'
}

var subClass = Object.create(superClass, {
    'newKid'     : {value:'the new kid on the block', writable: true, enumerable: true, configurable: true},
    'overridden' : {value:'not in my house'},
    'dumpSuper'  : {value:function(){
        console.log(this.overridden+" formerly was "+ Object.getPrototypeOf(this).overridden)
        }
    }
});
//console.log(subClass.property);
//console.log(subClass.overridden);
//console.log(subClass.newKid);
//subClass.newKid= 'actually I lived here before';
//console.log(subClass.newKid);

//subClass.dumpSuper();




//Object literals.
var objectLiterals = {
    'illegal-javascript-name': 'hidden on first look',
    'eins': 1,
    'zwei': 2,
    'sum': function(){
        return this.eins + this.zwei;
    }
}
//console.log(objectLiterals.eins);
//for the point notation, the name has to be a legal javascript name and no reserved keyword
//console.log(objectLiterals.illegal-javascript-name);
//console.log(objectLiterals['illegal-javascript-name']);
//console.log(objectLiterals.sum());


//The Module Pattern.
var singleton = (function(){
    var privateVariable = 7;
    function privateFunction(x){
        console.log(privateVariable+x);
    }

    return {
        firstMethod: function(a,b){
            privateFunction(3);
            console.log('first method');
        },
        secondMethod: function(c){
            console.log('second method');
        }
    };
}());
//singleton.firstMethod()