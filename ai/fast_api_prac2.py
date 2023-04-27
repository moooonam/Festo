from typing import Union, Annotated

from fastapi import FastAPI, Query, Path
from pydantic import BaseModel

# from typing_extensions import Annotated

class Item(BaseModel):
    name: str
    description: Union[str, None] = None
    price: float
    tax: Union[float, None] = None


app = FastAPI()


@app.put("/items/{item_id}")
async def create_item(item_id: int, item: Item, q: Union[str, None] = None):
    result = {"item_id": item_id, **item.dict()}
    if q:
        result.update({"q": q})
    return result



# @app.get("/items/")
# async def read_items(
#     q: Annotated[Union[str, None], Query(alise="test") ] = None
# ):
#     results = {"items": [{"item_id": "Foo"}, {"item_id": "Bar"}]}
#     if q:
#         results.update({"q": q})
#     return results

# @app.get("/items/1/")
# async def read_items(q: Annotated[list, Query()] = []):
#     query_items = {"q": q}
#     return query_items


@app.get("/items/{item_id}")
async def read_items(
    *,
    item_id: int = Path(title="The ID of the item to get", gt=0, le=1000),
    q: str,
):
    results = {"item_id": item_id}
    if q:
        results.update({"q": q})
    return results