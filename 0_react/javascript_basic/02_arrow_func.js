function sum1(x, y) { return x + y; }
const sum2 = function (x, y) { return x + y; }
const sum3 = (x, y) => { return x + y; }
const sum4 = (x, y) => x + y;

console.log(sum1(1, 2));
console.log(sum2(1, 2));
console.log(sum3(1, 2));
console.log(sum4(1, 2));
console.log(((x, y) => x + y)(1, 2));