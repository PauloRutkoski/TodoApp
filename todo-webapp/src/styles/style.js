import { Link } from "react-router-dom";
import styled, { createGlobalStyle } from "styled-components";

export const Colors = {
    primary: '#cb6ce6',
    secondary: '#4a1b57',
};

export const GlobalStyle = createGlobalStyle`
    * {
        margin: 0;
        padding: 0;
        outline: 0;
        box-sizing: border-box;
    }

    html, body, #root {
        min-height: 100%;
    }

    body {
        font-size: 16px;
        -webkit-font-smoothing: antialiased !important;
        font-family: Sans-Serif;
    }

    input, button {
        border-radius: 10px;
        padding: 10px;
        box-sizing: border-box;
    }

    input {
        width: 100%;
        border: none;
        font-size: 16px;
        caret-color: ${Colors.primary};
        border: 1px solid #f2f2f2;
        transition: 500ms;
    }

    input::selection {
        background: ${Colors.primary}4d;
        color: ${Colors.secondary};
    }

    input:focus {
        border: 1px solid ${Colors.primary};
    }

`;

export const Separator = styled.div`
    height: 15px;
`;

export const ElevatedButton = styled.button`
        cursor: pointer;
        width: 100%;
        display: inline-block;
        background-color: ${props => props.disabled ? '#f2f2f2' : Colors.primary};
        color: ${props => props.disabled ? Colors.secondary :'#FFF'};
        border: none;
        font-size: 16px;
        box-shadow:  ${props => props.disabled ? 'none' :'0 2px 4px #EBC6F6'};
        transition: 500ms;
        font-weight: bold;

        :hover {
            box-shadow: none;
        }
`;

export const NavLink = styled(Link)`
    text-decoration: none;
    color: ${Colors.primary};
    font-weight: 500;
`;

