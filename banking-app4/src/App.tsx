import React from 'react';
import logo from './logo.svg';
import './App.css';
import Dashboard from './dashboard/Dashboard';
import NavBar from './navbar/NavBar';


function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          learn react
        </a>
      </header>
    </div>
  );
}