const Nav = () => {
    const menuArr = [
        {link: "/", name: "HOME"},
        {link: "/about", name: "ABOUT"},
        {link: "/service", name: "SERVICE"},
        {link: "/product", name: "PRODUCT"},
    ]
    return ( 
        <nav>
            {
                menuArr.map(menu => {
                    return (
                        <li key={menu.name}><a href={menu.link}>{menu.name}</a></li>
                    )
                })
            }
        </nav>
    );
}
 
export default Nav;