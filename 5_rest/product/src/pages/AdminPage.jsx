import { useForm } from 'react-hook-form';
import { Container, TextField, Button, Typography, List, ListItem, ListItemText } from '@mui/material';
import { useEffect, useState } from 'react';
import { productAPI } from '../services/product';

const AdminPage = () => {
    const { register, handleSubmit, reset, setValue } = useForm();
    const [products, setProducts] = useState([]);
    const [editingProduct, setEditingProduct] = useState(null);

    const getAllProducts = async () => {
        try {
            const response = await productAPI.getAll();
            setProducts(response.data);
        } catch (error) {
            console.error('상품 가져오기 실패', error);
        }
    };

    useEffect(() => {
        getAllProducts();
    }, []);

    const onSubmit = async (data) => {
        try {
          if (editingProduct) {
            await productAPI.updateProduct({ ...data, id: editingProduct.id });
            setEditingProduct(null);
          } else {
            await productAPI.addProduct(data);
          }
          reset();
          getAllProducts();
        } catch (error) {
          console.error('상품 등록 또는 수정 실패', error);
        }
    };

    const handleEdit = (product) => {
        setEditingProduct(product);
        setValue('name', product.name);
        setValue('price', product.price)
    };

    const handleDelete = async (id) => {
        try {
            await productAPI.deletePost(id);
            getAllProducts();
        } catch (error) {
        console.error('삭제 실패', error);
        }
    };
    return (
        <Container>
            <Typography variant="h4" gutterBottom>
                Admin - Product Management
            </Typography>

            <form onSubmit={handleSubmit(onSubmit)}>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Product Name"
                    {...register('name', { required: true })}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Price"
                    type="number"
                    {...register('price', { required: true })}
                />
                <Button type="submit" variant="contained" color="primary" sx={{ mt: 2 }}>
                    {editingProduct ? 'Update Product' : 'Add Product'}
                </Button>
            </form>

            <Typography variant="h5" gutterBottom sx={{ mt: 4 }}>
                Product List
            </Typography>
            <List>
                {products.map((product) => (
                <ListItem key={product.id} divider>
                    <ListItemText primary={`${product.name} - $${product.price}`} />
                    <Button onClick={() => handleEdit(product)} color="primary">Edit</Button>
                    <Button onClick={() => handleDelete(product.id)} color="secondary">Delete</Button>
                </ListItem>
                ))}
            </List>
        </Container>
    );
}
 
export default AdminPage;