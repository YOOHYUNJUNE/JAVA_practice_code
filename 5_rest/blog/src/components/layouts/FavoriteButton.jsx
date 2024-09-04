// FavoriteButton.jsx
import React from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import Button from '@mui/material/Button';

const FavoriteButton = () => {
    const fetchFavorites = async () => {
        try {
            const response = await axios.get('/api/favorite');
            const favorites = response.data;

            const favoriteTitles = favorites.map(fav => fav.title).join(', ');

            Swal.fire({
                position: 'top-end',
                title: '즐겨찾기 목록',
                text: favoriteTitles,
                showConfirmButton: false,
            });
        } catch (error) {
            console.error('즐겨찾기를 가져올 수 없습니다.', error);
        }
    };

    return (
        <Button onClick={fetchFavorites} color="inherit">
            즐겨찾기
        </Button>
    );
};

export default FavoriteButton;
