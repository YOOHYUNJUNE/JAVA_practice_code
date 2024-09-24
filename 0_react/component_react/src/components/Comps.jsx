const Header = () => {
    return ( 
        <header>
            <h1>
                <a href='/'>React</a>
            </h1>
        </header>
     );
}

const Navi = () => {
    const menuList = [
        {name: "홈", link: "/"},
        {name: "추천", link: "/recommend"},
        {name: "뷰티", link: "/beauty"},
    ]
    return ( 
        <nav>
            {
                menuList.map((menu) => {
                    return (
                        <div>
                            <a href={menu.link}>
                                {menu.name}
                            </a>
                        </div>
                    )
                })
            }
        </nav>
     );
}

const Article = (props) => {
    const style = {
        textAlign: 'center',
        backgroundColor: "#aaa",
        height: '20vh',
        margin: '10px 50px',
    }
    return ( 
        <article style={style}>
            <h2>{props.text}</h2>
        </article>
     );
}
 
export { Header, Navi, Article};