import { api } from "./api";

const basePath = '/tasks';

export const findAll = async () => {
    return await api.get(`${basePath}`);
}

export const insert = async (title) => {
    return await api.post(basePath, title);
}

export const updateStatus = async (id, status) => {
    return await api.patch(`${basePath}/status/${id}`, status);
}

export const deleteTask = async (id) => {
    return await api.delete(`${basePath}/${id}`);
}