// 배열 메서드 map
// 배열 안의 요소들을 처리해서, [새로운 배열을 생성]
const arr = [1, 3, 5, 7, 9];
const mapArr1 = arr.map((value) => value + 1);
const mapArr2 = arr.map((value, index) => value + index);
const mapArr3 = arr.map((value, index, array) => value + array[4]);

console.log(mapArr1);
console.log(mapArr2);
console.log(mapArr3);