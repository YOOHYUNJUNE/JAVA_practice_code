const [name, ...info] = ["최민식", 50, "서울"];

console.log("name", name);
console.log("info", info);

const names = ['최민식', '유해진', '이도현', '김고은'];
const stars = ['이도현', ...names, ...names];
console.log(stars);

const fruits = ['사과', '오렌지', '딸기', '수박'];
const [apple, orange, ...rest] = fruits;
console.log(rest);

let me = {
    my_name: "최인규",
    my_age: 17,
    my_region: '서울',
    my_email: 'cik@naver.com'
}

const { my_name, my_age, ...my_rest } = me;
console.log(my_rest);