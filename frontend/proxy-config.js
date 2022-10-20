module.exports = [ 
    { context: ['/api/auth/signin'],
    target: 'http://localhost:8080',
    secure: false
    }
    // { context: ['/api/auth/signout'],
    // target: 'http://localhost:8080',
    // secure: false
    // }
    
]