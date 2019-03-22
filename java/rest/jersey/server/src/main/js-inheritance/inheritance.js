if (typeof Object.method !== 'function') {
    Object.prototype.method = function (name, func) {
        if (!this.prototype[name]) {
            this.prototype[name] = func;
        }
        return this;
    };
}

if (typeof Object.beget !== 'function') {
    Object.beget = function (o) {
        var F = function () {};
        F.prototype = o;
        return new F();
    };
}



var person = function(spec, my){
    var shared = my || {};

    var that = {};
    shared.name = spec.name;

    var sound = function(){
        console.log('['+shared.name+']: sound');
    }
    that.sound = sound;

    return that;
}

var thomas = person({name:'thomas'});
thomas.sound();

var superhero =function(spec){
    var my = {};
    var that = person(spec, my);

    var sound = function(){
        console.log('['+my.name+']: scream');
    }

    that.sound = sound;

    return that;
}

var conan = superhero({name:'conan'});
conan.sound();





String.method('bla', function(){
    alert('bla for all strings');
    return this;
}).method('blub', function(){
    alert('blub for all strings');
    return this;
});


