import { useEffect, useState } from "react";
import ProductBox from "./ProductBox";
import axios from "axios";

const Products = () => {
    const [productList, setProductList] = useState([]);

    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: 0
    });

    const handleChange = (e) => {
        const inputName = e.target.name;
        const inputValue = e.target.value;
        setNewProduct(prevState => ({...prevState, [inputName] : inputValue}))
    }

    const getProducts = async() => {
        const res = await axios.get("http://localhost:8080/product");
        const data = res.data;
        setProductList(data);
    }

    const handleAddProduct = async() => {
        try {
            const res = await axios.post("http://localhost:8080/product", newProduct);
            const data = res.data;
            setProductList([...productList, data]);
            setNewProduct({
                name: "",
                description: "",
                price: 0
            });
        } catch (err) {
            console.error(err);
        }
    }

    const exceptProduct = (id) => {
        setProductList(productList.filter(p => p.id != id));
    };

    const modifyProduct = (editedProduct) => {
        setProductList(productList.map(p => p.id == editedProduct.id ? editedProduct : p));
    }

    return (
        <main>
            상품 목록
            <button onClick={getProducts}>가져오기</button>

            <div style={{ display: 'flex', flexWrap: 'wrap' }}>
                {
                    productList.map(product => <ProductBox key={product.id} product={product} exceptProduct={exceptProduct} getProducts={getProducts} modifyProduct={modifyProduct}></ProductBox>)
                }
            </div>

            <div>
                <input name="name" value={newProduct.name} onChange={handleChange} />
                <input name="description" value={newProduct.description} onChange={handleChange} />
                <input name="price" type="number" value={newProduct.price} onChange={handleChange} />
                <button onClick={handleAddProduct}>버튼</button>
            </div>
        </main>
    );
}
 
export default Products;