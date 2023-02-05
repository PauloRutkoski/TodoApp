import { Navigate, Route, Routes } from "react-router-dom";
import SignInPage from "../pages/SignInPage";
import SignUpPage from "../pages/SignUpPage";
import TasksPage from "../pages/TasksPage";
import { getStoredAuthData, registerInterceptors } from "../services/api";

export default function Router() {
    return (
        <Routes>
            <Route exact path="/" element={<SignInPage/>} />
            <Route exact path="/sign-up" element={<SignUpPage />}  />
            <Route exact path="/tasks" element={<PrivateRoute component={<TasksPage />} />}  />
        </Routes>
    );
}

function PrivateRoute(props) {
    const authData = getStoredAuthData();
    registerInterceptors();

    const isAuthenticated = authData != null && authData.token != null;
    return isAuthenticated ? props.component : <Navigate to="/" />
}