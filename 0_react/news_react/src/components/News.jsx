import axios from "axios";
import { useState, useEffect } from "react";
import "./News.css";

const News = () => {
    const cateArr = ["business", "entertainment", "general", "health", "science", "sports", "technology"]
    const [newsList, setNewsList] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const [category, setCategory] = useState("business");

    const getNews = async() => {
        try {
            const apiKey = process.env.REACT_APP_API_KEY;
            const res = await axios.get(`https://newsapi.org/v2/top-headlines?country=kr&category=${category}&apiKey=${apiKey}`);
            console.log(res.data.articles);
            setNewsList(res.data.articles); 
            setIsLoading(false);
        } catch (error) {
            console.error(error);
        }
    }

    const handleCategory = (e) => {
        console.log(e.target.innerText);
        setCategory(e.target.innerText);
    }

    useEffect(() => {
        getNews();
    }, [category]);
    
    if (isLoading) {
        return (
            <div>로딩 중</div>
        )
    }

    return (
        <>
            {
                cateArr.map((category, idx) => (
                    <button key={idx} onClick={handleCategory}>{category}</button>
                ))
            }
            <div className="news-list">
                {newsList.map((article, idx) => (
                    <div key={idx} className="news-item">
                        <div className="thumbnail">
                            <img src={article.urlToImage} alt={article.title} />
                        </div>
                        <div className="contents">
                            <h2>{article.title}</h2>
                            <p>{article.description}</p>
                        </div>
                    </div>
                ))}
            </div>
        </>
    );
}
 
export default News;