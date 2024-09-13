import {useLocation} from 'react-router-dom';

const Error = () => {
    
    const {state} = useLocation();

    return (
        <>
            에러 ㄱㄱ
            <div>
                {state}
            </div>

        </>
    );
}
 
export default Error;