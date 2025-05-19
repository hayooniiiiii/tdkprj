import React, { useState } from 'react';
import {
    TextField, Button, Typography, Box, Paper,
    MenuItem, Select, InputLabel, FormControl
} from '@mui/material';
import axios from "axios";

export function Join() {
    const [formData, setFormData] = useState({
        userLoginid: '',
        userPassword: '',
        userName: '',
        userIpsa: '',
        userRole: '',
        buseoId: '',
    });

    const departments = [
        { id: 1, name: '정보시스템부' },
        { id: 2, name: 'scm부' },
        { id: 3, name: '기술부' },
        { id: 4, name: '품질보증부' },
        { id: 5, name: '기획부' },
        { id: 6, name: '경리재무부' },
        { id: 7, name: '계수관리부' },
        { id: 8, name: '인사관리부'}
    ];

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(formData);
            const response = await axios.post('/account/join', formData);
            console.log('서버 응답:', response.data);
            window.location.href = "/"
        } catch (error) {
            console.error('회원가입 실패:', error);
        }
    };

    return (
        <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
            <Paper elevation={3} sx={{ padding: 4, width: 400 }}>
                <Typography variant="h5" gutterBottom textAlign="center">회원가입</Typography>
                <form onSubmit={handleSubmit}>
                    <TextField
                        fullWidth label="아이디" margin="normal"
                        name="userLoginid" value={formData.userLoginid} onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="비밀번호" type="password" margin="normal"
                        name="userPassword" value={formData.userPassword} onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="이름" margin="normal"
                        name="userName" value={formData.userName} onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="입사일" type="date" InputLabelProps={{ shrink: true }} margin="normal"
                        name="userIpsa" value={formData.userIpsa} onChange={handleChange}
                    />
                    <TextField
                        fullWidth label="직책" margin="normal"
                        name="userRole" value={formData.userRole} onChange={handleChange}
                    />
                    <FormControl fullWidth margin="normal" variant="outlined">
                        <InputLabel>부서</InputLabel>
                        <Select
                            labelId="buseo-label"
                            name="buseoId"
                            value={formData.buseoId}
                            label="부서"
                            onChange={handleChange}
                        >
                            {departments.map((dept) => (
                                <MenuItem key={dept.id} value={dept.id}>
                                    {dept.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
                        회원가입
                    </Button>
                </form>
            </Paper>
        </Box>
    );
}
