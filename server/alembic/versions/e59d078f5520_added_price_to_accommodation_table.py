"""added price to accommodation table

Revision ID: e59d078f5520
Revises: 501bf5835207
Create Date: 2025-03-20 15:45:13.578772

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa
import sqlmodel


# revision identifiers, used by Alembic.
revision: str = 'e59d078f5520'
down_revision: Union[str, None] = '501bf5835207'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    """Upgrade schema."""
    pass


def downgrade() -> None:
    """Downgrade schema."""
    pass
