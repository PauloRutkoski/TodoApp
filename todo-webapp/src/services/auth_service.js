import { api } from "./api";

const basePath = '/auth';

export const authenticate = async (username, password) => {
        const userData =  {
            username: username,
            password: password
        };
        return await api.post(basePath, userData);
}

export const register = async (username, password) => {
    const userData =  {
        username: username,
        password: password
    };
    return await api.post(`${basePath}/register`, userData);
}

export const refresh = async (refreshToken) => {
    return await api.post(`${basePath}/refresh`, refreshToken);
}
