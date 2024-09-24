import { useEffect, useState } from "react";
import axios from 'axios';
import ProdInput from "./product/ProdInput";
import ProdBox from "./product/ProdBox";
import { useReducer } from "react";

const productReducer = (state, action) => {
    switch (action.type) {
        case "SET_PRODUCTS":
            return action.payload;
        case "ADD_PRODUCT":
            return [...state, action.payload];
        case "EDIT_PRODUCT":
            return state.map(p => (action.payload.id == p.id ? action.payload : p));
        case "DELETE_PRODUCT":
            return state.filter(p => ( p.id != action.payload));
    }
}

const Product = () => {
    // const [products, setProducts] = useState([]);
    const [products, dispatch] = useReducer(productReducer, []);
    const [loading, setLoading] = useState(true);
    const [err, setErr] = useState(false);
    // const getProducts = () => {
    //     axios.get('http://localhost:8080/products')
    //         .then(res => res.data)
    //         .then(data => setProducts(data))
    //         .catch(err => console.error(err))
    // }

    const getProducts = async() => {
        try {
            const res = await axios.get('http://localhost:8080/products');
            setLoading(false);
            const data = res.data;
            dispatch({type: "SET_PRODUCTS", payload: data});
        } catch (err) {
            console.error(err);
            setErr(true)
        }
    }

    useEffect(() => {
        getProducts();
    }, []);

    if (err) {
        return <div>에러 발생</div>
    }

    if (loading) {
        return <div>로딩 중...</div>
    }

    return ( 
        <main>
            <div style={{display: "flex", flexWrap: "wrap"}}>
                {
                    products.map(prod => {
                        return (
                            <ProdBox key={prod.id} prod={prod} dispatch={dispatch} />
                        )
                    })
                }
            </div>
            <ProdInput dispatch={dispatch} />
        </main>
     );
}
 
export default Product;