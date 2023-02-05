import styled from "styled-components";
import { Colors } from "../../styles/style";

export const Container = styled.div`
    height: 100vh;
    background-image: linear-gradient(to bottom, ${Colors.primary}, ${Colors.secondary});
    display: flex;
    justify-content: center;
    align-items: center;
`;

export const Form = styled.form`
    width: 90%;
    max-width: 500px;
    border-radius: 15px;
    background: #fff;
    border-radius: 16px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    padding: 20px;

    h6 {
        font-weight: 300;
        font-size: 14px;
    }
`;

