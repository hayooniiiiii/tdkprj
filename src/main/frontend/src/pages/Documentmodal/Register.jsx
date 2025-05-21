// Register.js
import React, {forwardRef, useEffect, useImperativeHandle, useState} from 'react';
import {
    Card, CardContent, Grid, TextField, Typography, Table,
    TableHead, TableRow, TableCell, TableBody, TableContainer,
    Paper, Button, Box, Checkbox
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';
import Vcationtype from "../Vacation/Vcationtype";

// 1) 공통 룰 객체 정의
const fullDayRule = {
    fixedDate: false,
    start: "08:30",
    end: "17:00",
    autoUsage: true,
    disableUsage: true,
    disableTime: true
};
const halfDayRule = {
    fixedDate: true,
    start: "08:30",
    end: "12:30",
    usageDays: 0.5,
    autoUsage: false,
    disableUsage: true,
    disableTime: true
};
const halfDayRulePm = {
    ...halfDayRule,
    start: "12:30",
    end: "17:00"
};
const tripRule = {
    fixedDate: false,
    start: "08:30",
    end: "17:00",
    autoUsage: false,
    disableUsage: true,
    disableTime: false
};
const dateOnlyRule = {
    fixedDate: true,
    autoUsage: false,
    disableUsage: true,
    disableTime: false
};

// 2) 유형별 키 배열
const fullDayTypes = [
    "연차", "경조휴가", "대체휴가(종일)",
    "특별휴가(정년)", "조기귀속휴가",
    "생리휴가", "특별휴가(해외출장)"
];
const halfDayTypesAm = ["전반차", "대체휴가(전반)"];
const halfDayTypesPm = ["후반차", "대체휴가(후반)"];
const tripTypes     = ["국내출장", "해외출장"];
const dateOnlyTypes = [
    "훈련", "교육", "외출(공용)", "외출(사용)",
    "조퇴", "결근", "검진휴가"
];

// 3) vacationRules 생성
const vacationRules = {
    복직: { fixedDate: false, start: "08:30", end: "17:00", autoUsage: false, disableUsage: true, disableTime: true },
    ...Object.fromEntries(fullDayTypes.map(type => [type, fullDayRule])),
    ...Object.fromEntries(halfDayTypesAm.map(type => [type, halfDayRule])),
    ...Object.fromEntries(halfDayTypesPm.map(type => [type, halfDayRulePm])),
    ...Object.fromEntries(tripTypes.map(type => [type, tripRule])),
    ...Object.fromEntries(dateOnlyTypes.map(type => [type, dateOnlyRule]))
};

const Register = forwardRef((props,ref) => {
    const [profile, setProfile] = useState({});
    const [buseoName, setBuseoName] = useState('');
    const [rows, setRows] = useState([
        { id: 1, vacationType: '', startDate: '', endDate: '', startTime: '', endTime: '', usageDays: 0, note: '' }
    ]);
    const [selectedRowIds, setSelectedRowIds] = useState([]);
    const [vacationModalOpen, setVacationModalOpen] = useState(false);
    const [selectedRowIndex, setSelectedRowIndex] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/main/profile', { withCredentials: true })
            .then(res => { setProfile(res.data.user); setBuseoName(res.data.buseoName); })
            .catch(err => console.error('프로필 가져오기 실패:', err));
    }, []);

    // 행 추가
    const handleAddRow = () => {
        setRows(prev => [
            ...prev,
            { id: prev.length + 1, vacationType: '', startDate: '', endDate: '', startTime: '', endTime: '', usageDays: 0, note: '' }
        ]);
    };

    // 행 삭제
    const handleDeleteRows = () => {
        setRows(prev => prev.filter(r => !selectedRowIds.includes(r.id)));
        setSelectedRowIds([]);
    };

    // 체크박스 토글
    const handleSelectRow = (id) => {
        setSelectedRowIds(prev =>
            prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
        );
    };

    // 모달 오픈
    const handleOpenModal = idx => { setSelectedRowIndex(idx); setVacationModalOpen(true); };

    // 휴가 유형 선택
    const handleSelectVacation = item => {
        setRows(prev => {
            const rule = vacationRules[item];
            const updated = { ...prev[selectedRowIndex], vacationType: item, startDate: '', endDate: '', startTime: rule.start, endTime: rule.end, usageDays: rule.usageDays ?? 0, note: '' };
            const next = [...prev]; next[selectedRowIndex] = updated; return next;
        });
        setVacationModalOpen(false);
    };

    // 셀 변경
    const handleRowChange = (idx, field, val) => {
        setRows(prev => prev.map((r, i) => {
            if (i !== idx) return r;
            const updated = { ...r, [field]: val };
            const rule = vacationRules[updated.vacationType] || {};
            if (field === 'startDate' && rule.fixedDate) updated.endDate = val;
            if (rule.autoUsage && updated.startDate && updated.endDate) {
                const diff = (new Date(updated.endDate) - new Date(updated.startDate)) / (1000*60*60*24) + 1;
                updated.usageDays = diff > 0 ? diff : 0;
            }
            return updated;
        }));
    };

    const sumUsage = rows.reduce((sum, r) => sum + Number(r.usageDays || 0), 0);

    useImperativeHandle(ref, () => ({
        getData: () => rows  // ✅ vacationDto 객체 안 감싸고 배열로 바로
    }));

    return (
        <Card sx={{ borderRadius: 2, width: '100%' }}>
            <CardContent sx={{ p: 3 }}>
                <Typography variant="subtitle1" fontWeight="bold" gutterBottom>신청현황</Typography>
                <Box mb={2} display="flex" justifyContent="flex-end" gap={1}>
                    <Button
                        startIcon={<AddIcon />}
                        onClick={handleAddRow}
                        variant="contained"
                        sx={{ backgroundColor: '#009688', color: '#fff', '&:hover': { backgroundColor: '#00796b' } }}
                    >행 추가</Button>
                    <Button
                        variant="outlined"
                        sx={{
                            color: '#009688',
                            borderColor: '#009688',
                            '&:hover': {
                                backgroundColor: '#009688',
                                color: 'white',
                                borderColor: '#00796b',
                            },
                        }}
                        startIcon={<DeleteIcon />}
                        onClick={handleDeleteRows}
                        variant="outlined"
                        disabled={selectedRowIds.length === 0}
                    >행 삭제</Button>
                </Box>
                <TableContainer component={Paper}>
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell sx={{ width: 40, p: 0.5 }}>
                                    <Checkbox
                                        size="small"
                                        checked={selectedRowIds.length === rows.length}
                                        indeterminate={selectedRowIds.length > 0 && selectedRowIds.length < rows.length}
                                        onChange={() => {
                                            if (selectedRowIds.length === rows.length) setSelectedRowIds([]);
                                            else setSelectedRowIds(rows.map(r => r.id));
                                        }}
                                    />
                                </TableCell>
                                <TableCell sx={{ width: 100, p: 0.5 }}>휴가유형</TableCell>
                                <TableCell sx={{ width: 100, p: 0.5 }}>신청시작일</TableCell>
                                <TableCell sx={{ width: 100, p: 0.5 }}>신청종료일</TableCell>
                                <TableCell sx={{ width: 80, p: 0.5 }}>시작시간</TableCell>
                                <TableCell sx={{ width: 80, p: 0.5 }}>종료시간</TableCell>
                                <TableCell sx={{ width: 80, p: 0.5 }} align="right">사용일수</TableCell>
                                <TableCell sx={{ p: 0.5 }}>내용</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {rows.map((row, idx) => {
                                const rule = vacationRules[row.vacationType] || {};
                                const disabledAll = !row.vacationType;
                                return (
                                    <TableRow key={row.id}>
                                        <TableCell sx={{ p: 0.5 }}>
                                            <Checkbox
                                                size="small"
                                                checked={selectedRowIds.includes(row.id)}
                                                onChange={() => handleSelectRow(row.id)}
                                            />
                                        </TableCell>
                                        <TableCell
                                            onClick={() => handleOpenModal(idx)}
                                            sx={{ cursor: 'pointer', color: '#009688', p: 0.5 }}
                                        >{row.vacationType || '클릭'}</TableCell>
                                        <TableCell sx={{ p: 0.5 }}>
                                            <TextField
                                                type="date"
                                                value={row.startDate}
                                                onChange={e => handleRowChange(idx, 'startDate', e.target.value)}
                                                disabled={disabledAll}
                                                InputLabelProps={{ shrink: true }}
                                                size="small"
                                            />
                                        </TableCell>
                                        <TableCell sx={{ p: 0.5 }}>
                                            <TextField
                                                type="date"
                                                value={row.endDate}
                                                onChange={e => handleRowChange(idx, 'endDate', e.target.value)}
                                                disabled={disabledAll || rule.fixedDate}
                                                InputLabelProps={{ shrink: true }}
                                                size="small"
                                            />
                                        </TableCell>
                                        <TableCell sx={{ p: 0.5 }}>
                                            <TextField
                                                type="time"
                                                value={row.startTime}
                                                onChange={e => handleRowChange(idx, 'startTime', e.target.value)}
                                                disabled={disabledAll || rule.disableTime}
                                                InputLabelProps={{ shrink: true }}
                                                size="small"
                                            />
                                        </TableCell>
                                        <TableCell sx={{ p: 0.5 }}>
                                            <TextField
                                                type="time"
                                                value={row.endTime}
                                                onChange={e => handleRowChange(idx, 'endTime', e.target.value)}
                                                disabled={disabledAll || rule.disableTime}
                                                InputLabelProps={{ shrink: true }}
                                                size="small"
                                            />
                                        </TableCell>
                                        <TableCell sx={{ p: 0.5 }} align="right">
                                            {rule.disableUsage || disabledAll
                                                ? row.usageDays.toFixed(2)
                                                : (
                                                    <TextField
                                                        type="text"
                                                        value={row.usageDays}
                                                        onChange={e => handleRowChange(idx, 'usageDays', parseFloat(e.target.value) || 0)}
                                                        disabled={disabledAll}
                                                        inputProps={{ step: 0.01 }}
                                                        size="small"
                                                    />
                                                )
                                            }
                                        </TableCell>
                                        <TableCell sx={{ p: 1 }}>
                                            <TextField
                                                fullWidth
                                                value={row.note}
                                                onChange={e => handleRowChange(idx, 'note', e.target.value)}
                                                disabled={disabledAll}
                                                size="medium"
                                            />
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                            <TableRow>
                                <TableCell colSpan={6} align="right" sx={{ p: 0.5 }}>∑</TableCell>
                                <TableCell align="right" sx={{ p: 0.5 }}>{sumUsage.toFixed(2)}</TableCell>
                                <TableCell sx={{ p: 0.5 }} />
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
                <Box mt={1} display="flex" justifyContent="flex-end" pr={2}>
                    <Typography variant="body2" fontWeight="bold">
                        사용일수 합계: {sumUsage.toFixed(2)}
                    </Typography>
                </Box>
                <Vcationtype
                    open={vacationModalOpen}
                    onClose={() => setVacationModalOpen(false)}
                    onSelect={handleSelectVacation}
                />
            </CardContent>
        </Card>
    );
});

export default Register;