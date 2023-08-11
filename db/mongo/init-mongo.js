db.createUser({
        user: 'root',
        pwd: 'toor',
        roles: [
            {
                role: 'readWrite',
                db: 'testDB',
            },
        ],
    });
db.createCollection('app_users', { capped: false });

db.app_users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        "password": "$2a$10$L8o/MBSO3K5.MBYQs2o34.cKWm1GY8JNsAihxyY2Al3srGwbf4.ka",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        "password": "$2a$10$dZOuZMLsGFH4W6UCDRwzhOqijVBtJO05mJ08X3NJt4G4ouaN9A4F6",
        "role":
        {
            "granted_authorities": ["ROLE_USER"]
        }
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        "password": "$2a$10$bNsdFFFBQiwoFgvbqKujee29j8nbAKy0BUonq3qLMZazFX27SJHgm",
        "role":
        {
            "granted_authorities": ["ROLE_USER", "ROLE_ADMIN"]
        }
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        "password": "$2a$10$lMH2GUPJQ4/3Py8vztOK5OlOpH68/6RpEeuA1Df/TI3bbCvyoih2y",
        "role":
        {
            "granted_authorities": ["ROLE_ADMIN"]
        }
    }
]);