import { library } from '@fortawesome/fontawesome-svg-core';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { faCheck, faRightFromBracket, faSpinner, faXmark } from '@fortawesome/free-solid-svg-icons';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import Router from './routes';
import { GlobalStyle } from './styles/style';

const root = ReactDOM.createRoot(document.getElementById('root'));
library.add(fab, faSpinner);
library.add(fab, faCheck);
library.add(fab, faXmark);
library.add(fab, faRightFromBracket);

root.render(
  <React.StrictMode>
    <>
      <GlobalStyle/>
      <BrowserRouter>
        <Router/>
      </BrowserRouter>
    </>
  </React.StrictMode>
);