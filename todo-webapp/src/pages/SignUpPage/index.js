import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoadingButton from "../../components/LoadingButton";
import { register } from "../../services/auth_service";
import { NavLink, Separator } from "../../styles/style";
import { Container, Form } from "./style";

export default function SignUpPage() {
    const [userData, setUserData] = useState({});
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    function handleSignUp(e) {
        e.preventDefault();
        setLoading(true);

        register(userData.username, userData.password)
        .then(handleSingUpSuccess)
        .catch(handleSingUpError)
        .finally(() => setLoading(false));
    }
    
    function handleSingUpSuccess(response) {
        if (response.status === 201) {
            alert('User successfully created');
            navigate('/');
        }
    }

    function handleSingUpError(e) {
        if (e.response == null || e.response.status === 500) {
            alert('Something wrong happened during the request');
        } else {
            alert('This username is already in use');
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
            <Form onSubmit={handleSignUp}>
                <h1>Register your account</h1>
                <h5>Let's start working!</h5>
                <Separator/>

                <input name="username" onChange={handleChangeUserData} placeholder="Username"/>
                <Separator/>
                <input name="password" onChange={handleChangeUserData} placeholder="Password" type='password'/>
                <Separator/>
                
                <LoadingButton type="submit" loading={loading} >Sign Up</LoadingButton>
                
                <Separator/>
                <h6>Already have an account? <NavLink to='/'>Sign In</NavLink></h6>
            </Form>
        </Container>
    );
}