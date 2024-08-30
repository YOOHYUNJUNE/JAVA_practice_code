const Navigation = () => {
    const menuList = [
        {link: "/about", name: "ABOUT"},
        {link: "/service", name: "SERVICE"},
        {link: "/product", name: "PRODUCT"}
    ];

    return ( 
        <nav>
            <ul>
                {
                    menuList.map(menu => <li><a href={menu.link}>{menu.name}</a></li>)
                }
            </ul>
        </nav>
    );
}
 
export default Navigation;