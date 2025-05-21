// Vcationtype.js
import React, { useState, useEffect } from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, TextField, Table, TableHead, TableBody,
    TableRow, TableCell, TableContainer, Paper, Box
} from '@mui/material';

const vacationItems = [
    "연차", "전반차", "후반차", "국내출장", "해외출장", "경조휴가",
    "대체휴가(전반)", "대체휴가(후반)", "대체휴가(종일)", "특별휴가(정년)",
    "조기귀속휴가", "훈련", "교육", "외출(공용)", "외출(사용)", "생리휴가",
    "조퇴", "결근", "검진휴가", "복직", "특별휴가(해외출장)"
];

const Vcationtype = ({ open, onClose, onSelect }) => {
    const [searchText, setSearchText] = useState('');
    const [filteredItems, setFilteredItems] = useState(vacationItems);
    const [selectedItem, setSelectedItem] = useState(null);

    useEffect(() => {
        const filtered = vacationItems.filter(item =>
            item.toLowerCase().includes(searchText.toLowerCase())
        );
        setFilteredItems(filtered);
    }, [searchText]);

    const handleSelectConfirm = () => {
        if (selectedItem) {
            onSelect(selectedItem);
            onClose();
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>근태항목</DialogTitle>
            <DialogContent>
                <Box display="flex" gap={1} mb={2}>
                    <TextField
                        fullWidth
                        placeholder="조건입력"
                        value={searchText}
                        onChange={(e) => setSearchText(e.target.value)}
                    />
                </Box>

                <TableContainer component={Paper}>
                    <Table size="small">
                        <TableHead>
                            <TableRow>
                                <TableCell width="10%">No.</TableCell>
                                <TableCell>근태항목</TableCell>
                                <TableCell>비고</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {filteredItems.map((item, index) => (
                                <TableRow
                                    key={item}
                                    hover
                                    onClick={() => setSelectedItem(item)}
                                    selected={selectedItem === item}
                                    sx={{ cursor: 'pointer' }}
                                >
                                    <TableCell>{index + 1}</TableCell>
                                    <TableCell>{item}</TableCell>
                                    <TableCell />
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </DialogContent>
            <DialogActions>
                <Button
                    onClick={handleSelectConfirm}
                    variant="contained"
                    disabled={!selectedItem}
                >
                    선택
                </Button>
                <Button onClick={onClose} variant="outlined">
                    닫기
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default Vcationtype;
