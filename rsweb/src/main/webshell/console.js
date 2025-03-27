const config = require('config');
let port;
let host;

if ( Object.hasOwn(config, 'server') ) {
  port = config.server.port;
  host = config.server.host;
} else {
    port = '8081';
    host = 'localhost';
}

import {Terminal} from 'xterm';
import {WebLinksAddon} from 'xterm-addon-web-links';
import {FitAddon} from 'xterm-addon-fit';
import {RSocketAddon} from './xterm-addon-rsocket';
import "xterm/css/xterm.css";

class XtermConsole extends HTMLElement {
    constructor() {
        super();
        let rsocketUrl = 'ws://'  + host + ':'+ port +'/rsocket';
        let container = document.createElement('div');
        container.style.cssText = 'width: 100%; height: 100%;';
        this.append(container);
        //initialize xterm
        let term = new Terminal();
        term.prompt = () => {
            term.write('\r\nshell>');
        };
        //load addons
        term.loadAddon(new WebLinksAddon());
        let fitAddon = new FitAddon();
        term.loadAddon(fitAddon);
        term.loadAddon(new RSocketAddon(rsocketUrl));
        term.open(container);
        fitAddon.fit();
        term.writeln('RSS TERMINAL');
        term.prompt();
        term.focus();
        this.terminal = term;
    }
}

window.customElements.define('xterm-console', XtermConsole);
