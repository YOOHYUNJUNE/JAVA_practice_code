// 배열 메서드 filter
// 콜백 조건에 충족하는 요소만을 추출해 [새로운 배열 반환]

const arr = [1, 2, 3, 4, 5, 6];
// const mapArr1 = arr.map(value => value % 2 == 1);
// console.log(mapArr1);

const filterArr1 = arr.filter(value => value % 2 == 1);
console.log(filterArr1);

const filterArr2 = arr.filter((value, index) => index > 3);
console.log(filterArr2);

const filterArr3 = arr.filter((value, index, array) => value > array[2]);
console.log(filterArr3);