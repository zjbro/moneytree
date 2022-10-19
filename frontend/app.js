import {Request, Response} from "express";
import * as express from 'express';
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
import * as jwt from 'jsonwebtoken';
import * as fs from "fs";
import { validateAndRewriteCoreSymbol } from "@angular/compiler-cli/src/ngtsc/imports";


const app: Application = express();

app.use(bodyParser.json);

app.route('api/login')
    .post(loginRoute);

const RSA_PRIVATE_KEY = fs.readFileSync('./demos.private.key');

export function loginRoute (req: Request, res: Response) {
    
    const username = req.body.username, 
        password = req.body.password;

    if (validateUsernameAndPassword()) {
        const userId = findUserIdForUsername(username);

        const jwtBearerToken = jwt.sign({}, RSA_PRIVATE_KEY, {
            algorithm: 'RS256',
            expiresIn: 120,
            subject: userId
        })
    }

    else {
        // send status 401 Unauthorized
        res.sendStatus(401);
    }

    // session token created above
    const jwtBearerToken = jwt.sign(...);

    // set it in a HTTP Only + Secure Cookie
    res.cookie("SESSIONID", jwtBearerToken, {httpOnly:true, secure:true});

    // set it in the HTTP Response body
    res.status(200).json({
        idToken: jwtBearerToken,
        expiresIn: ...
    });
    
}