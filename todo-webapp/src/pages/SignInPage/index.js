import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoadingButton from "../../components/LoadingButton";
import { storeAuthData } from "../../services/api";
import { authenticate } from "../../services/auth_service";
import { NavLink, Separator } from "../../styles/style";
import { Container, Form } from "./style";

export default function SignInPage() {
    const [userData, setUserData] = useState({});
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    async function handleSignIn(e) {
        e.preventDefault();
        setLoading(true);

        authenticate(userData.username, userData.password)
        .then(handleSuccessResponse)
        .catch(onFail)
        .finally(() => setLoading(false));
    }

    async function handleSuccessResponse(response) {
        const authData = response.data;
        storeAuthData(authData);
        
        navigate('/tasks');
    }

    function onFail(e) {
        const response = e.response;
        if (response == null || response.status === 500) {
            alert('We had a problem during the request');
        } else {
            alert('Invalid Username/Password');            
        }
    }

    function handleChangeUserData(e) {
        const data = {
            ...userData,
            [e.target.name] : e.target.value,
        };
        setUserData(data);
    }

    return (
        <Container>
            <Form onSubmit={handleSignIn}>
                <h1>Welcome Back</h1>
                <h5>Let's go back to work!</h5>
                <Separator/>

                <input name="username" onChange={handleChangeUserData} placeholder="Username"/>
                <Separator/>
                <input name="password" onChange={handleChangeUserData} placeholder="Password" type='password'/>
                <Separator/>
                
                <LoadingButton type="submit" loading={loading} >Sign in</LoadingButton>
                
                <Separator/>
                <h6>Not registered yet? <NavLink to='/sign-up'>Sign up</NavLink></h6>
            </Form>
        </Container>
    );
}