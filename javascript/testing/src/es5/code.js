const ConstructorMethod = function () {
  this.value = 42;
};

ConstructorMethod.prototype.sampleMethod = function (parameter) {
  return this.value + parameter;
};

exports.ConstructorMethod = ConstructorMethod;
