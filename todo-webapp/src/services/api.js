import axios from 'axios';
import { refresh } from './auth_service';

const authDataKey = 'TodoApp_auth';

export const config = {
    'Content-Type': 'application/json',
    'accept':'application/json'
};

export const api = axios.create({
        baseURL: 'http://localhost:8080/todo',
        headers: config,
    });

export function storeAuthData(authData) {
    localStorage.setItem(authDataKey, JSON.stringify(authData));
}

export function signout() {
    localStorage.setItem(authDataKey, '');
    api.interceptors.request.clear();
    api.interceptors.response.clear();
}

export function getStoredAuthData() {
    const data = localStorage.getItem(authDataKey);
    if(data) {
        return JSON.parse(data);
    }
    return null;
}

export function registerInterceptors() {
    const authData = getStoredAuthData();
    if (authData) {
        registerAuthInterceptor(authData.token);
        registerRefreshInterceptor(authData.refreshToken);
    }
}

function registerAuthInterceptor(token) {
    api.interceptors.request.use((request) => {
        if (!request.url.includes('/auth')) {
            request.headers.set('Authorization', token);
        }
        return request;
    });
}

function registerRefreshInterceptor(refreshToken) {
    api.interceptors.response.use(
        (response) => response,
        async (e) => {
            if (e.response) {
                if (e.response.status === 401 && !e.response.request.url.includes('/auth')) {
                    try {
                        const authData = await refresh(refreshToken);
                        storeAuthData(authData);
                        return;
                    } catch (err) {
                        console.error(e);
                    }
                }
            }
            return Promise.reject(e);
        }
    );
}
