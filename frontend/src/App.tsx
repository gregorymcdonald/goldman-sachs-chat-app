import * as React from 'react';
import { render } from 'react-dom';
import Chat from './Chat';

import './App.scss';

class App extends React.Component {  
    render () {
      return (
        <div className="container">
            <Chat />
        </div>
      );
    }
  }

render(
    <App />,
    document.getElementById('root')
);