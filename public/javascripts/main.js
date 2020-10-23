const getCellValue = (tr, idx) => tr.children[idx].innerText || tr.children[idx].textContent;

const comparer = (idx, asc) => (a, b) => ((v1, v2) =>
        v1 !== '' && v2 !== '' && !isNaN(v1) && !isNaN(v2) ? v1 - v2 : v1.toString().localeCompare(v2)
)(getCellValue(asc ? a : b, idx), getCellValue(asc ? b : a, idx));

// do the work...
document.querySelectorAll('th').forEach(th => th.addEventListener('click', (() => {
    const table = th.closest('table');
    Array.from(table.querySelectorAll('tr:nth-child(n+2)'))
        .sort(comparer(Array.from(th.parentNode.children).indexOf(th), this.asc = !this.asc))
        .forEach(tr => table.appendChild(tr) );
})));

function checkCol(row, cells, filter) {
    const matches = (el) => row.cells[el].textContent.toUpperCase().indexOf(filter) > -1
    return cells.some(matches)
}

function filterTable(selector, cells){
    function inner(event) {
        let filter = event.target.value.toUpperCase();
        let rows = document.querySelector(selector).rows;
        console.log(rows.length)
        for (let i = 1; i < rows.length; i++) {
            if (checkCol(rows[i], cells, filter)) {
                console.log("here")
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
                console.log("here")
            }
        }
    }
    return inner
}

const test = () => console.log("Hi")

let genericTableFilter = filterTable("#generic_table", [3, 4])
let mainTableFilter = filterTable("#stock_table", [0, 1, 2, 3])

try {
    document.querySelector('#search_input').addEventListener('keyup', genericTableFilter, false)
} catch (error) {
    document.querySelector('#main_table_search_bar').addEventListener('keyup', mainTableFilter, false)
}
