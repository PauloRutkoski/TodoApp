import styled from "styled-components";
import LoadingButton from "../../components/LoadingButton";
import { Colors } from "../../styles/style";

export const Container = styled.div`
    display: flex;
    flex-direction: column;
    margin: 20px auto;
    width: 100%;
    max-width: 600px;
    padding: 10px;
    align-items: stretch;
`;

export const TaskInput = styled.input`
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
    font-size: 20px;
    font-weight: lighter;
`;

export const TaskSubmit = styled(LoadingButton)`
    border-radius: 0px 5px 5px 0px;
    max-width: 50px;
    display: inline-block;
`;

export const AppLogo = styled.img`
     max-width: 200px;
     width: 30%;
     min-width: 100px;
     margin-bottom: 30px;
     align-self: center;
`;

export const TaskForm = styled.form `
    display: flex;
`;

export const TaskTile = styled.li `
    list-style: none;
    border-bottom: 1px solid #f2f2f2;
    padding: 8px 10px;
    transition: 300ms;
    font-weight: lighter;
    display: flex;
    box-sizing: border-box;
    
    :hover {
        border-bottom: 1px solid ${Colors.primary};
        font-size: 1.1rem;
    }
`;

export const TileContent = styled.div`
    display: flex;
    flex-grow: 1;
`;

export const Check = styled.div `
    width: 20px;
    height: 20px;
    display: flex;
    justify-content: center;
    transition: 800ms;
    align-items: center;
    border-radius: 20px;
    margin-right: 10px;
    border: 1px solid #f2f2f2;
    cursor: pointer;
    background-image: ${props => props.selected ? `linear-gradient(to bottom right, ${Colors.primary}, ${Colors.secondary})` : 'none'};

    :hover { 
        border: 1px solid ${props => props.selected ? 'none' : Colors.primary};
    }
`;

export const TileActions = styled.div`
    align-self: flex-end;
    display: flex;
`;

export const TileAction = styled.button`
    background-color: transparent;
    border: none;
    padding: 0;
    width: 20px;
    cursor: pointer;
`;

export const LoadingContainer = styled.div`
    display: flex;
    justify-content: center;
    color: ${Colors.primary};
    margin-top: 20px;
`;

export const Header = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 15px 20px;

    button {
        background-color: transparent;
        border: none;
        padding: 0;
        font-weight: bold;
        color: ${Colors.primary};
        cursor: pointer;
    }
`;

export const Username = styled.div`
    font-weight: bold;
    color: ${Colors.primary};
`;

