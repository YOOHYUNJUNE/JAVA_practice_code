import { Container, Typography, List, ListItem, ListItemText } from '@mui/material';
import { productAPI } from '../services/product';
import { useEffect, useState } from 'react';

const ProductPage = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const getAllProducts = async () => {
            try {
                const response = await productAPI.getAll();
                setProducts(response.data);
            } catch (error) {
                console.error('상품 가져오기 실패', error);
            }
        };
        getAllProducts();
    }, []);

    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Product List
            </Typography>
            <List>
                {products.map((product) => (
                <ListItem key={product.id}>
                    <ListItemText primary={`${product.name} - $${product.price}`} />
                </ListItem>
                ))}
            </List>
        </Container>
    );
}
 
export default ProductPage;