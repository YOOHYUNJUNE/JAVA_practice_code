import { useEffect, useReducer, useState } from "react";
import ProductBox from "./ProductBox";
import axios from "axios";

// CRUD
const productReducer = (state, action) => {
    switch (action.type) {
        case "SET_PRODUCT":
            return action.payload; // "SET" 인경우, payload로 세팅
        case "ADD_PRODUCT":
            return [...state, action.payload];
        case "EDIT_PRODUCT":
            return state.map(p => p.id == action.payload.id ? action.payload : p);
        case "DELETE_PRODUCT":
            return state.filter(p => p.id != action.payload.id);
    }
}


const Products = () => {
    
    const [productList, dispatch] = useReducer(productReducer, []);

    // const [productList, setProductList] = useState([]);
    const [loading, setLoading] = useState(true);

    // 에러 페이지
    const [errorPage, setErrorPage] = useState(false);

    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: 0
    });

    const handleChange = (e) => {
        const inputName = e.target.name;
        const inputValue = e.target.value;
        setNewProduct(prevState => ({ ...prevState, [inputName]: inputValue }))
    }

    const getProducts = async () => {
        try {
            const res = await axios.get("http://localhost:8080/product");
            const data = res.data;
            // setProductList(data);
            dispatch({type: "SET_PRODUCT", payload: data})
            setLoading(false) // 제품을 가져오면 로딩 -> false            
        } catch (error) {
            console.error(error);
            setErrorPage(true);
        }
    }

    const handleAddProduct = async () => {
        try {
            const res = await axios.post("http://localhost:8080/product", newProduct);
            const data = res.data;
            // setProductList([...productList, data]);
            dispatch({type:"ADD_PRODUCT", payload: data})
            setNewProduct({
                name: "",
                description: "",
                price: 0
            });
        } catch (err) {
            console.error(err);
            setErrorPage(true);
        }
    }

    // 새로고침시 바로 데이터 반영
    useEffect(() => {
        getProducts();
    }, []);

    // 에러 화면
    if (errorPage) {
        return <main>에러 발생</main>
    }

    // 로딩 화면
    if (loading) {
        return <main>로딩 중...</main>
    }

    return (
        <main>
            상품 목록
            {/* <button onClick={getProducts}>가져오기</button> */}

            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {
                    productList.map(product => <ProductBox key={product.id} product={product} dispatch={dispatch} getProducts={getProducts}></ProductBox>)
                }
            </div>

            <div>
                <input name="name" value={newProduct.name} onChange={handleChange} />
                <input name="description" value={newProduct.description} onChange={handleChange} />
                <input name="price" type="number" value={newProduct.price} onChange={handleChange} />
                <button onClick={handleAddProduct}>제품추가</button>
            </div>
        </main>
    );
}

export default Products;