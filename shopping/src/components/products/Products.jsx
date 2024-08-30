import { useEffect, useState } from "react";
import ProductBox from "./ProductBox";
import axios from "axios";

const Products = () => {
    const [productList, setProductList] = useState([]);

    const getProducts = async() => {
        const res = await axios.get("http://localhost:8080/product");
        const data = res.data;
        setProductList(data);
    }

    return (
        <main>
            상품 목록
            <button onClick={getProducts}>가져오기</button>

            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {
                    productList.map(product => <ProductBox key={product.id} product={product}></ProductBox>)
                }
            </div>
        </main>
    );
}
 
export default Products;