import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Logo from '../../assets/logo.png';
import { getStoredAuthData, signout } from "../../services/api";
import { deleteTask, findAll, insert, updateStatus } from "../../services/tasks_service";
import { Separator } from "../../styles/style";
import { AppLogo, Check, Container, Header, LoadingContainer, TaskForm, TaskInput, TaskSubmit, TaskTile, TileAction, TileActions, TileContent, Username } from "./style";

export default function TasksPage() {
    const [tasks, setTasks] = useState([]);
    const [title, setTitle] = useState('');
    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);
    const [username, setUsername] =  useState('');
    const navigate = useNavigate();

    useEffect(() => {
        setUsername(getStoredAuthData().username);
        find();
    }, []);

    function find() {
        setLoading(true);
        findAll()
        .then(onFindPage)
        .catch(onFindPageError);
        setLoading(false);
    
        function onFindPage(response) {
            let data = [];
            if (response.status === 200)
                data = response.data;
            setTasks(data);
        }
    
        function onFindPageError(e) {
            if (e.status === undefined) {
                console.error(e);
            }
            alert('We had a problem during the request ' + e.status);
        }
    }

   function onTitleChange(e) {
        setTitle(e.target.value);
   }

   function handleSubmit(e) {
        e.preventDefault();
        if (submitting) {
            return;
        }
        setSubmitting(true);
        save();
        setSubmitting(false);
   }

   function save() {
        insert(title)
        .then( _ => {
            setTitle('');
            find();
        })
        .catch( e => {
            if (e.response.status === 400) {
                alert(e.response.data);
            } else {
                alert('We had a problem during the request');
            }
        });
   }

   function handleCheck(task) {
        const status = task.status === 'DONE' ? 'UNDONE' : 'DONE';

        updateStatus(task.id, status)
        .then((_) => find())
        .catch( _ => alert('We had a problem during the request'));
   }

   function handleDelete(task) {
        deleteTask(task.id)
        .then((_) => find())
        .catch( _ => alert('We had a problem during the request'));
   }

   function logout() {
        signout();
        navigate('/');
   }

    return (
        <div>
            <Header >
                <Username> Hello {username} </Username>
                <button onClick={() => logout()} title="Log out"> 
                    <FontAwesomeIcon icon="right-from-bracket" />
                </button>
            </Header>
            <Container>
                <AppLogo src={Logo} alt="logo"/>
                <TaskForm onSubmit={handleSubmit}>
                    <TaskInput value={title} placeholder="What's the next task?" onChange={onTitleChange}/>
                    <TaskSubmit loading={submitting}>
                        <FontAwesomeIcon icon="check" />
                    </TaskSubmit>
                </TaskForm>
                <Separator />
                <TaskList list={tasks} handleCheck={handleCheck} handleDelete={handleDelete} loading={loading} />
            </Container>            
        </div>
    );
}

function TaskList({list, handleCheck, handleDelete, loading}) {

    if (loading) {
        return (
            <LoadingContainer>
                <FontAwesomeIcon icon="spinner" className="fa-spin fa-2x" />
            </LoadingContainer>
        );
    }
    
    return (
        <ul>
            { list.map((i) => {
                return (
                    <TaskTile key={i.id}>
                        <TileContent>
                            <Check selected={i.status === 'DONE'} onClick={() => handleCheck(i)}>
                                {i.status === 'DONE' ? <FontAwesomeIcon icon="check" style={{color: '#fff', fontSize: '11px'}} /> : <></>}
                            </Check>
                            {i.title}
                        </TileContent>

                        <TileActions>
                            <TileAction onClick={() => handleDelete(i)} title='Delete Task'>
                                <FontAwesomeIcon icon="xmark"/>
                            </TileAction>
                        </TileActions>
                    </TaskTile>
                );
            }) }
        </ul>
    );
}